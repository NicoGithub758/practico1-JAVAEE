package uy.edu.fing.tse.demo2023.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.edu.fing.tse.demo2023.util.qualifier.TSE2023DB;

@ApplicationScoped
public class EntityManagerProducer {

	@Produces
	@PersistenceContext(unitName="TSE2023")
	@TSE2023DB
	private EntityManager em;
	
}
