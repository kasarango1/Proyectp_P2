package com.espe.nucleo;

import com.espe.bean.CertificadoBean;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayOutputStream;

public class FirmaPdf {
    public static byte[] firmaPdfBasico(byte[] data, CertificadoBean certificado) throws Exception
    {
        try {
            //libreria que nos ayuda
            PdfReader reader = new PdfReader(data);
            //para almacenar el flujo de bytes
            ByteArrayOutputStream nuevoDocumento = new ByteArrayOutputStream();
            PdfStamper stp = PdfStamper.createSignature(reader, nuevoDocumento, '\000', null, true);
            PdfSignatureAppearance sap = stp.getSignatureAppearance();

            sap.setCrypto(certificado.getClavePrivada(), certificado.getCadenaDeCertificados(), null, PdfSignatureAppearance.WINCER_SIGNED);
            sap.setReason("Firma Digital");
            sap.setLocation("Quito");
            sap.setVisibleSignature(new Rectangle(100,100,350,200), 1, "sig");
            stp.close();

            return nuevoDocumento.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}