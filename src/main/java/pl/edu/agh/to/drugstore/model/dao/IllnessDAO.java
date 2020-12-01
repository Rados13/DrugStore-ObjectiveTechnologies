package pl.edu.agh.to.drugstore.model.dao;

import javax.persistence.EntityManager;

public class IllnessDAO {

    private final EntityManager em;

    public IllnessDAO(EntityManager em) {
        this.em = em;
    }

    // TODO: methods to manage Illness table
}
