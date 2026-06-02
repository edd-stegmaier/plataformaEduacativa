package com.duoc.plataformaEducativa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.duoc.plataformaEducativa.dto.S3ObjectDTO;
import com.duoc.plataformaEducativa.service.S3Service;

import java.util.List;

@RestController
@RequestMapping("api/s3")
public class S3Controller {
    
    @Autowired
    private S3Service s3Service;

    // Listar objetos de un bucket
    @GetMapping("/{bucket}/objects")
    public ResponseEntity<List<S3ObjectDTO>> listObjects(@PathVariable String bucket){

        List<S3ObjectDTO>list = s3Service.listObjects(bucket);
        return ResponseEntity.ok(list);
    }

    // Descargar objecto como arreglo de bytes
    @GetMapping("/{bucket}/object")
    public ResponseEntity<byte[]> donwloadObject(@PathVariable String bucket, @RequestParam String key){

        byte[] fileBytes = s3Service.downloadAsBytes(bucket, key);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileBytes);
    }

    // Subir archivo a S3
    @PostMapping("/{bucket}/object")
    public ResponseEntity<Void> uploadObject(@PathVariable String bucket, @RequestParam String key,
        @RequestParam("file") MultipartFile file){

        s3Service.upload(bucket, key, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Mover un objeto en un bucket
    @PostMapping("/{bucket}/move")
    public ResponseEntity<Void> moveObject(@PathVariable String bucket, @RequestParam String sourceKey,
			@RequestParam String destKey) {

		s3Service.moveObject(bucket, sourceKey, destKey);
		return ResponseEntity.ok().build();
	}

    // Eliminar objeto en S3
    @DeleteMapping("/{bucket}/object")
	public ResponseEntity<Void> deleteObject(@PathVariable String bucket, @RequestParam String key) {

		s3Service.deleteObject(bucket, key);
		return ResponseEntity.noContent().build();
	}




} 
