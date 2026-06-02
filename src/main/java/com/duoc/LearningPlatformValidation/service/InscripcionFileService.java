package com.duoc.LearningPlatformValidation.service;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.duoc.LearningPlatformValidation.model.InscripcionEntity;

@Service
public class InscripcionFileService {

    private final S3Service s3Service;
    private final String bucket = "bucket-desarrollo-cloud";

    public InscripcionFileService(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    // Sube resumen a bucket S3
    public String subirResumen(InscripcionEntity inscripcion) throws Exception{
		
		byte[] resumen = generarResumenIncripcion(inscripcion);
		String key = generarResumenKey(inscripcion);

		s3Service.upload(bucket, key, resumen);

		return key;
	}

    //Genera resumen en bytes
    public byte[] generarResumenIncripcion(InscripcionEntity inscripcion) throws Exception{

        if(inscripcion == null || inscripcion.getCurso() == null || inscripcion.getEstudiante() == null){
            throw new Exception("La inscripcion no puede ser nula");
        }
        
        StringBuilder sb = new StringBuilder();

        sb.append("----------------------------------\n");
        sb.append(" Resumen de Inscripcion de cursos\n");
        sb.append("----------------------------------\n");

        sb.append("curso: ").append(inscripcion.getCurso().getNombre()).append("\n");
        sb.append("estudiante: ").append(inscripcion.getEstudiante().getNombre()).append("\n");
        sb.append("fecha inscripcion: ").append(inscripcion.getFechaInscripcion()).append("\n");

        byte[] archivo = sb.toString().getBytes(StandardCharsets.UTF_8);

        return archivo;
    }

    // genera key resumen
    public String generarResumenKey(InscripcionEntity inscripcion){
        String id = inscripcion.getId().toString();
        String key = "resumenInscripcion-".concat(id).concat("/inscripcion-").concat(id).concat(".txt");
        return key;
    }


}
