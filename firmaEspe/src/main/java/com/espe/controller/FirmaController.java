package com.espe.controller;

import com.espe.bean.CertificadoBean;
import com.espe.nucleo.CertificadoLee;
import com.espe.nucleo.FirmaPdf;
import com.espe.util.Constante;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FirmaController {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/gestion")
    public String gestion() {
        return "gestion";
    }
    
    @GetMapping("/firma")
    public String mostrarFormulario() {
        return "firma";
    }
    
    @PostMapping("/firma/firmar")
    public ResponseEntity<?> firmarPdf(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("rutaGuardado") String rutaGuardado) {
        try {
            // Validar que el archivo no sea nulo
            if (archivo.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Por favor seleccione un archivo PDF");
            }

            // Obtener el certificado
            CertificadoBean certificado = CertificadoLee.getCertificadoDeArchivo(
                    Constante.CERTIFICADO, 
                    Constante.CLAVE
            );
            
            // Convertir el archivo a bytes y firmarlo
            byte[] documentoOriginal = archivo.getBytes();
            byte[] documentoFirmado = FirmaPdf.firmaPdfBasico(documentoOriginal, certificado);
            
            if (documentoFirmado == null) {
                return ResponseEntity.badRequest()
                        .body("Error al firmar el documento");
            }
            
            // Crear nombre del archivo firmado
            String nombreOriginal = archivo.getOriginalFilename();
            String nombreFirmado = nombreOriginal.substring(0, nombreOriginal.lastIndexOf(".")) 
                                 + "_firmado.pdf";
            
            // Asegurarse de que el directorio existe
            Path directorio = Paths.get(rutaGuardado);
            if (!Files.exists(directorio)) {
                Files.createDirectories(directorio);
            }
            
            // Guardar el archivo
            Path rutaCompleta = directorio.resolve(nombreFirmado);
            Files.write(rutaCompleta, documentoFirmado);
            
            // Devolver el archivo firmado para descarga
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                           "attachment; filename=\"" + nombreFirmado + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(documentoFirmado);
                    
        } catch (Exception e) {
            e.printStackTrace(); // Para debugging
            return ResponseEntity.badRequest()
                    .body("Error al firmar el PDF: " + e.getMessage());
        }
    }
} 