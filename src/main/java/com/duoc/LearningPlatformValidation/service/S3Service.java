package com.duoc.LearningPlatformValidation.service;

import com.duoc.LearningPlatformValidation.dto.S3ObjectDTO;
import com.duoc.LearningPlatformValidation.exception.InvalidFileException;
import com.duoc.LearningPlatformValidation.exception.S3FileException;

import io.awspring.cloud.s3.S3Exception;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    
    private final S3Client s3Client;

    // Listar objetos de un bucket S3
    public List<S3ObjectDTO> listObjects(String bucket){
        try {
        
            log.info("Listando objetos del bucket {}", bucket);

            ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucket).build();
            ListObjectsV2Response response = s3Client.listObjectsV2(request);

            log.info("Se encontraron {} objetos en el bucket {}", response.contents().size(), bucket);

            
            return response.contents().stream()
                    .map(obj -> new S3ObjectDTO(obj.key(), obj.size(),
                            obj.lastModified() != null ? obj.lastModified().toString() : null))
                    .collect(Collectors.toList());
            
        } catch (NoSuchBucketException e) {
            throw new S3FileException("No se encontro el bucket: " + e.getMessage(), e);
        } catch (S3Exception e) {
            throw new S3FileException("Error al listar objetos: " + e.getMessage(), e);
        }

    }

    // Descargar objeto de S3 como bytes[]
    public byte[] downloadAsBytes(String bucket, String key){

        try {
            log.info("Descargando objeto: {} del bucket: {}", key, bucket);

            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();
            ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);

            log.info("Objeto descargado exitosamente: {}", key);

            return responseBytes.asByteArray();

        } catch (NoSuchBucketException e) {
            throw new S3FileException("No se encontro el bucket: " + e.getMessage(), e);
        } catch (NoSuchKeyException e) {
            throw new S3FileException("El objeto no existe en el bucket: " + e.getMessage(), e);
        } catch (S3Exception e) {
            throw new S3FileException("Error al descargar objeto: " + e.getMessage(), e);
        }

    }

    //Subir archivo a S3
    public void upload(String bucket, String key, MultipartFile file){

        if(file == null || file.isEmpty()){
            throw new InvalidFileException("Archivo vacio o nulo");
        }
        if(file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()){
            throw new InvalidFileException("Nombre de archivo no valido");
        }
        if(file.getSize() == 0){
            throw new InvalidFileException("El archivo tienme tamaño 0");
        }

        try {
            log.info("Subiendo archivo: {} al bucket {}, tamaño: {} bytes", key, bucket, file.getSize());

            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucket).key(key)
                 .contentType(file.getContentType()).contentLength(file.getSize()).build();
                
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            log.info("Archivo subido exitosamente {}", key);

        } catch (NoSuchBucketException e) {
            throw new S3FileException("No se encontro el bucket: " + e.getMessage(), e);
        } catch (S3Exception e) {
            throw new S3FileException("Error al subir objeto: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new S3FileException("Error al leer el objeto: " + e.getMessage(), e);
        }
    }

    //Subir archivo en Bytes a S3
    public void upload(String bucket, String key, byte[] bytes){

        if(bytes == null || bytes.length == 0){
            throw new InvalidFileException("Archivo en bytes vacio o nulo");
        }

        try {
            log.info("Subiendo archivo: {} al bucket {}, tamaño: {} bytes", key, bucket, bytes.length);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucket).key(key)
                 .contentLength((long) bytes.length).build();
                
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));

            log.info("Archivo subido exitosamente {}", key);

        } catch (NoSuchBucketException e) {
            throw new S3FileException("No se encontro el bucket: " + e.getMessage(), e);
        } catch (S3Exception e) {
            throw new S3FileException("Error al subir objeto: " + e.getMessage(), e);
        }
    }


    // Mover objeto dentro de un bucket
    public void moveObject(String bucket, String sourceKey, String destKey){

        try {
            log.info("Moviendo objeto de {} a {} en el bucket: {}", sourceKey, destKey, bucket);

            CopyObjectRequest copyRequest = CopyObjectRequest.builder().sourceBucket(bucket).sourceKey(sourceKey)
                .destinationBucket(bucket).destinationKey(destKey).build();

            s3Client.copyObject(copyRequest);

            log.info("Objeto copiado exitosamente, eliminado el orgigen");

            deleteObject(bucket, sourceKey);

            log.info("Objeto movido exitosamente de {} a {}", sourceKey, destKey);

        } catch (NoSuchBucketException e) {
            throw new S3FileException("No se encontro el bucket: " + e.getMessage(), e);
        } catch (NoSuchKeyException e) {
            throw new S3FileException("El objeto no existe en el bucket: " + e.getMessage(), e);
        } catch (S3Exception e) {
            throw new S3FileException("Error al mover objeto: "+ e.getMessage(), e);
        }

    }

    // Eliminar objeto de S3
    public void deleteObject(String bucket, String key){

        try {
            log.info("Eliminando objeto: {} del bucket {}", key, bucket);

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
            s3Client.deleteObject(deleteRequest);

            log.info("Objeto eliminado exitosamente: {}", key);

        } catch (NoSuchBucketException e) {
            throw new S3FileException("No se encontro el bucket: "+ e.getMessage(), e);
        } catch (S3Exception e) {
            throw new S3FileException("Error al eliminar objeto: " + e.getMessage(), e);
        }

    }

}