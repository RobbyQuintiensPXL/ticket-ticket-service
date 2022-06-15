package be.jevents.ticketservice.config;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class PDFGenerator {

    @Value("${pdfDir}")
    private String uploadPath;

    public void generatePDF(String titel){
        Document document = new Document();

        try{
            PdfWriter.getInstance(document, new FileOutputStream(getPdfName("test")));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk(titel, font);

            document.add(chunk);
            document.close();


        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getPdfName(String titel) {
        return titel+".pdf";
    }
}
