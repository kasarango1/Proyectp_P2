package com.espe.nucleo;

import com.espe.bean.CertificadoBean;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;


public class CertificadoLee {
    public static CertificadoBean getCertificadoDeArchivo(String path, String key)
    {   CertificadoBean certificado = new CertificadoBean();
        try
        {   //certificados que contienen la clave privada
            KeyStore jks = KeyStore.getInstance("PKCS12");
            InputStream in = new FileInputStream(path);
            jks.load(in, key.toCharArray());
            in.close();
            //obtenemos el alias
            String aliasJks = jks.aliases().nextElement();
            //obtenemos la llave privada
            PrivateKey pk = (PrivateKey) jks.getKey(aliasJks, key.toCharArray());
            //obtenego la cadena de certificacion
            Certificate[] chain = jks.getCertificateChain(aliasJks);
            //obtenego el certificado principal
            X509Certificate oPublicCertificate = (X509Certificate) chain[0];

            certificado.setAlias(oPublicCertificate.getSubjectDN().getName());  //alias propio del certificao
            certificado.setCertificadoPublico(oPublicCertificate);
            certificado.setClavePrivada(pk);
            certificado.setCadenaDeCertificados(chain);
        } catch (Exception e)
        {   e.printStackTrace();
        }
        return certificado;
    }
}