package serveur.serveurjeux.Controller;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import serveur.serveurjeux.Entity.Facture;
import serveur.serveurjeux.Entity.TokenPack;
import serveur.serveurjeux.Entity.User;
import serveur.serveurjeux.Repository.FactureRepository;
import serveur.serveurjeux.Repository.TokenRepository;
import serveur.serveurjeux.Repository.UserRepository;

import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class FactureController {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/factures-creer",method = RequestMethod.POST)
    public String creerFacture(@RequestParam("packId") Long packId) {
        System.out.println("🔍 Requête POST reçue pour créer une facture, packId : " + packId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String pseudo = authentication.getName();
            User user = userRepository.findByUsername(pseudo);
        }

        String pseudo = authentication.getName();
        User user = userRepository.findByUsername(pseudo);

        TokenPack tokenPack = tokenRepository.findById(packId).orElse(null);
        user.setTokens(user.getTokens() + tokenPack.getTokens());
        userRepository.save(user);

        Facture facture = new Facture(user, tokenPack);
        factureRepository.save(facture);

        System.out.println("✅ Facture créée avec succès !");

        return "redirect:/tokens";
    }

    @RequestMapping(value = "/mesFactures",method = RequestMethod.GET)
    public String afficherMesFactures(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String pseudo = authentication.getName();
            User user = userRepository.findByUsername(pseudo);
        }


        String pseudo = authentication.getName();
        User user = userRepository.findByUsername(pseudo);

        List<Facture> factures = factureRepository.findByClientId(user.getId());
        model.addAttribute("factures", factures);
        model.addAttribute("content", "mesFactures.html");
        return "base";
    }

    @GetMapping("/factures/download/{id}")
    public void downloadFacture(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Facture facture = factureRepository.findById(id).orElse(null);
        if (facture == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Facture non trouvée");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=facture_" + id + ".pdf");

        Document document = new Document(PageSize.A4, 30, 30, 20, 20);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Polices
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLACK);
            Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
            Font boldFont = new Font(Font.HELVETICA, 10, Font.BOLD);
            Font tableHeaderFont = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);
            Font totalFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.BLUE);

            // En-tête de l'entreprise
            Paragraph companyHeader = new Paragraph("🏢 VORTX-ENGINE", titleFont);
            companyHeader.setAlignment(Element.ALIGN_CENTER);
            document.add(companyHeader);

            Paragraph companyInfo = new Paragraph(
                    "42000 Saint-Étienne\n" + "\n\n",
                    normalFont);
            companyInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(companyInfo);

            // Titre de la facture
            Paragraph invoiceTitle = new Paragraph("📄 FACTURE #" + facture.getId(), boldFont);
            invoiceTitle.setAlignment(Element.ALIGN_CENTER);
            invoiceTitle.setSpacingAfter(15);
            document.add(invoiceTitle);

            // Informations Client et Facture
            PdfPTable clientTable = new PdfPTable(2);
            clientTable.setWidthPercentage(100);
            clientTable.setSpacingBefore(10);
            clientTable.setSpacingAfter(20);
            clientTable.setWidths(new float[]{1, 1});

            // Colonne Client
            PdfPCell clientCell = new PdfPCell();
            clientCell.setBorder(Rectangle.NO_BORDER);
            clientCell.addElement(new Paragraph("🧑 Client :", boldFont));
            clientCell.addElement(new Paragraph("📧 Email : " + facture.getClient().getEmail(), normalFont));

            // Colonne Facture
            PdfPCell factureCell = new PdfPCell();
            factureCell.setBorder(Rectangle.NO_BORDER);
            factureCell.addElement(new Paragraph("📅 Date : " +
                    facture.getDateFacture().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont));
            factureCell.addElement(new Paragraph("💳 Mode de paiement : Carte bancaire", normalFont));
            factureCell.addElement(new Paragraph("🆔 Numéro de Facture : " + facture.getId(), normalFont));

            clientTable.addCell(clientCell);
            clientTable.addCell(factureCell);
            document.add(clientTable);

            // Tableau des détails de la facture
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 2, 1, 1});
            table.setSpacingBefore(10);

            // En-têtes stylées
            addStyledTableHeader(table, "Pack");
            addStyledTableHeader(table, "Nombre de Tokens");
            addStyledTableHeader(table, "Prix Unitaire (€)");
            addStyledTableHeader(table, "Prix Total (€)");

            // Contenu du tableau
            table.addCell(new PdfPCell(new Phrase(facture.getTokenPack().getName(), normalFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(facture.getTokenPack().getTokens()), normalFont)));
            table.addCell(new PdfPCell(new Phrase(
                    String.format("%.2f", facture.getTokenPack().getPrice() / 100.0), normalFont)));
            table.addCell(new PdfPCell(new Phrase(
                    String.format("%.2f", facture.getPrixTTC() / 100.0), boldFont)));

            document.add(table);

            // Calculer le Total TTC en euros
            double totalTTC = facture.getPrixTTC() / 100.0;
// Calculer le sous-total (HT) en divisant par 1.20
            double sousTotal = totalTTC / 1.20;
// Calculer la TVA : différence entre TTC et HT
            double tva = totalTTC - sousTotal;

// Récapitulatif total
            PdfPTable recapTable = new PdfPTable(2);
            recapTable.setWidthPercentage(50);
            recapTable.setSpacingBefore(15);
            recapTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            recapTable.setWidths(new float[]{1, 1});

// Sous-total
            recapTable.addCell(new PdfPCell(new Phrase("Sous-total :", boldFont)));
            recapTable.addCell(new PdfPCell(new Phrase(
                    String.format("%.2f €", sousTotal), normalFont)));

// TVA (20%)
            recapTable.addCell(new PdfPCell(new Phrase("TVA (20%) :", boldFont)));
            recapTable.addCell(new PdfPCell(new Phrase(
                    String.format("%.2f €", tva), normalFont)));

// Total TTC
            PdfPCell totalLabel = new PdfPCell(new Phrase("Total TTC :", totalFont));
            totalLabel.setBackgroundColor(Color.LIGHT_GRAY);
            totalLabel.setPadding(5);
            PdfPCell totalValue = new PdfPCell(new Phrase(
                    String.format("%.2f €", totalTTC), totalFont));
            totalValue.setBackgroundColor(Color.LIGHT_GRAY);
            totalValue.setPadding(5);

            recapTable.addCell(totalLabel);
            recapTable.addCell(totalValue);
            document.add(recapTable);

            // Remerciements
            Paragraph footer = new Paragraph(
                    "\nMerci pour votre confiance ! 😊\n" +
                            "Pour toute question, contactez-nous via le support",
                    normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

        } catch (DocumentException e) {
            throw new RuntimeException("Erreur lors de la création du PDF", e);
        } finally {
            document.close();
        }
    }

    // fonction pour styliser les en-têtes de tableau
    private void addStyledTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell(new Phrase(headerTitle, new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE)));
        header.setBackgroundColor(new Color(63, 81, 181)); // Bleu
        header.setPadding(5);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
    }


}
