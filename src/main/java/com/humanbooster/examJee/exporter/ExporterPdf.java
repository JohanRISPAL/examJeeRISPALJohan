package com.humanbooster.examJee.exporter;

import com.humanbooster.examJee.model.Annonce;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ExporterPdf {

    private List<Annonce> annonces;

    public ExporterPdf(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Id", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Contenu", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date de publication", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Annonce annonce : annonces) {
            table.addCell(String.valueOf(annonce.getId()));
            table.addCell(annonce.getContenu());
            table.addCell(String.valueOf(annonce.getPublicationDate()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Liste des annonces", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 12.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
