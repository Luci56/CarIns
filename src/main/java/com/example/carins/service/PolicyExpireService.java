package com.example.carins.service;

import com.example.carins.model.InsurancePolicy;
import com.example.carins.repo.InsurancePolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//clasa creata pentru a rezolva taskul 4 cu cron
public class PolicyExpireService {
    private final InsurancePolicyRepository policyRepository;

    //folosim final pentru evita reinstantierea lang tine id urile asigurariilor si booleanul verifica daca i a fost anuntata saui nu expirarea
    // practic pentru a evita afisarea aceluiasi mesaj pentru aceasi asigurare de mai multe ori
    private final Map<Long, Boolean> loggedPolicies = new HashMap<>();

    //loger pentru a afisa mesaje in consola
    private static final Logger log = LoggerFactory.getLogger(PolicyExpireService.class);

    public PolicyExpireService(InsurancePolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    //aici afcem cronul care verifica din 5 in 5 minute asigurarile expirate
    @Scheduled(fixedRate = 30000)
    public void logRecentlyExpiredPolicies() {

        //data de azi
        LocalDate now = LocalDate.now();

        //preluam toate asigurarile
        List<InsurancePolicy> policies = policyRepository.findAll();

        for (InsurancePolicy policy : policies) {

            LocalDate endDate = policy.getEndDate();
            //verificam daca asigurarea expira azi sau a expirat ieri
            boolean isRecentlyExpired = endDate.isEqual(now) || endDate.isEqual(now.minusDays(1));
            //daca a expirat recent si nu a fost dat mesajul deja
            if (isRecentlyExpired && loggedPolicies.containsKey(policy.getId())) {

                //afisam mesajul o singura data
                log.info("Policy {} for car {} expired on {}", policy.getId(), policy.getCar().getId(), endDate);

                //punem in map ca si cum a fost anuntata
                loggedPolicies.put(policy.getId(), true);

            }
        }

    }


}
