package serveur.serveurjeux.Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Inventaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "inventaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaseInventaire> caseInventaires = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CaseInventaire> getCaseInventaires() {
        return caseInventaires;
    }

    public void setCaseInventaires(List<CaseInventaire> caseInventaires) {
        this.caseInventaires = caseInventaires;
    }


    public boolean addCaseInventaire(CaseInventaire caseInventaire) {
        if (this.caseInventaires.size() < 40) {
            this.caseInventaires.add(caseInventaire);
            caseInventaire.setInventaire(this);
            return true;
        } else {
            System.out.println("Inventaire plein !");
            return false;
        }
    }
}
