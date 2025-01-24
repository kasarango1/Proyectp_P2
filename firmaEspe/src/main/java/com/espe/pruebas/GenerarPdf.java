package com.espe.pruebas;

import com.espe.bean.CertificadoBean;
import com.espe.nucleo.CertificadoLee;
import com.espe.nucleo.FirmaPdf;
import com.espe.util.Constante;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GenerarPdf {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Directorio predeterminado donde se buscarán los PDFs
            System.out.println("Ingrese el directorio donde se encuentran los PDFs (ejemplo: C:/MisDocumentos):");
            String directorio = scanner.nextLine();
            
            // Listar archivos PDF en el directorio
            File dir = new File(directorio);
            File[] archivos = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".pdf"));
            
            if (archivos == null || archivos.length == 0) {
                System.out.println("No se encontraron archivos PDF en el directorio: " + directorio);
                return;
            }
            
            // Mostrar archivos disponibles
            List<String> rutasPDF = new ArrayList<>();
            System.out.println("\nArchivos PDF disponibles:");
            for (int i = 0; i < archivos.length; i++) {
                System.out.println((i + 1) + ". " + archivos[i].getName());
                rutasPDF.add(archivos[i].getAbsolutePath());
            }
            
            // Seleccionar archivo
            System.out.println("\nIngrese el número del archivo que desea firmar (1-" + archivos.length + "):");
            int seleccion = Integer.parseInt(scanner.nextLine());
            
            if (seleccion < 1 || seleccion > archivos.length) {
                System.out.println("Selección inválida");
                return;
            }
            
            String rutaPDF = rutasPDF.get(seleccion - 1);
            System.out.println("Archivo seleccionado: " + rutaPDF);
            
            // Directorio para guardar el PDF firmado
            System.out.println("\nIngrese el directorio donde desea guardar el PDF firmado (ejemplo: C:/MisDocumentos):");
            String directorioSalida = scanner.nextLine();
            
            // Crear nombre para archivo firmado
            String nombreOriginal = new File(rutaPDF).getName();
            String nombreFirmado = nombreOriginal.substring(0, nombreOriginal.lastIndexOf(".")) + "_firmado.pdf";
            String rutaPDFFirmado = directorioSalida + File.separator + nombreFirmado;
            
            // Proceso de firma
            CertificadoBean certificado = CertificadoLee.getCertificadoDeArchivo(Constante.CERTIFICADO, Constante.CLAVE);
            Path path = Paths.get(rutaPDF);
            byte[] documento = Files.readAllBytes(path);
            documento = FirmaPdf.firmaPdfBasico(documento, certificado);
            
            // Guardar archivo firmado
            FileOutputStream out = new FileOutputStream(rutaPDFFirmado);
            out.write(documento);
            out.close();
            
            System.out.println("\n¡PDF firmado exitosamente!");
            System.out.println("Archivo guardado en: " + rutaPDFFirmado);

        } catch (NumberFormatException e) {
            System.err.println("Error: Debe ingresar un número válido");
        } catch (Exception e) {
            System.err.println("Error al procesar el PDF: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}