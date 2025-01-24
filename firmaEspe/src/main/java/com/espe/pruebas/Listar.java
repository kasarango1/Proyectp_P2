package com.espe.pruebas;

import com.espe.bean.CertificadoBean;
import com.espe.nucleo.CertificadoLee;
import com.espe.util.Constante;

public class Listar {
    public static void main(String[] args) {
        try {
            CertificadoBean certificado= CertificadoLee.getCertificadoDeArchivo(Constante.CERTIFICADO, Constante.CLAVE);
            System.out.println(certificado.getAlias());
            System.out.println("----------------------------------------------");
            System.out.println(certificado.getClavePrivada().getAlgorithm());
            System.out.println("----------------------------------------------");
            System.out.println(certificado.getCertificadoPublico().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}