package com.sinosoft.sinoep.sendFLowWorkflow.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class SendFlowInfoDaoImpl implements SendFlowInfoDaoExt {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void batchInsert(List list) {
        for (int i= 0;i<list.size();i++){
            this.entityManager.persist(list.get(i));
            if (i % 20 == 0){
                this.entityManager.flush();
                this.entityManager.clear();
            }
        }
    }
}
