package com.espe.bean;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class CertificadoBean {
    private PrivateKey clavePrivada;
    private Certificate[] cadenaDeCertificados;
    private String alias;
    private X509Certificate certificadoPublico;

    public PrivateKey getClavePrivada() {
        return clavePrivada;
    }

    public void setClavePrivada(PrivateKey clavePrivada) {
        this.clavePrivada = clavePrivada;
    }

    public Certificate[] getCadenaDeCertificados() {
        return cadenaDeCertificados;
    }

    public void setCadenaDeCertificados(Certificate[] cadenaDeCertificados) {
        this.cadenaDeCertificados = cadenaDeCertificados;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public X509Certificate getCertificadoPublico() {
        return certificadoPublico;
    }

    public void setCertificadoPublico(X509Certificate certificadoPublico) {
        this.certificadoPublico = certificadoPublico;
    }
}
