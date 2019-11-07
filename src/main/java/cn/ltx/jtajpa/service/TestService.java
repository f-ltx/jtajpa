package cn.ltx.jtajpa.service;

import cn.ltx.jtajpa.dao.lee.ExpenditureDao;
import cn.ltx.jtajpa.dao.sid.AccountDao;
import cn.ltx.jtajpa.model.lee.Expenditure;
import cn.ltx.jtajpa.model.sid.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TestService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ExpenditureDao expenditureDao;

    @Transactional
    public String testJtaAtomikos() {
        Account account = new Account();
        account.setAccountBalance(1111.11f);
        account = accountDao.save(account);

        Expenditure expenditure = new Expenditure();
        expenditure.setMoney(2222.22f);
        expenditure = expenditureDao.save(expenditure);
        int i = 1 / 0;
        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("expenditure", expenditure);
        return map.toString();
    }

    public String query() {
        List<Account> allAccounts = accountDao.findAll();
        List<Expenditure> allEexpenditures = expenditureDao.findAll();
        Map<String, Object> map = new HashMap<>();
        map.put("allAccounts", allAccounts);
        map.put("allEexpenditures", allEexpenditures);
        return map.toString();
    }

    @Transactional
    public String test1() {
        Account account = new Account();
        account.setAccountBalance(1.1f);
        account = accountDao.save(account);
        return account.toString();
    }

    @Transactional
    public String test2() {
        Expenditure expenditure = new Expenditure();
        expenditure.setMoney(2.2f);
        expenditure = expenditureDao.save(expenditure);
        return expenditure.toString();
    }
}