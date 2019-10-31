package cn.ltx.jtajpa.service;

import cn.ltx.jtajpa.dao.lee.ExpenditureDao;
import cn.ltx.jtajpa.dao.sid.AccountDao;
import cn.ltx.jtajpa.model.lee.Expenditure;
import cn.ltx.jtajpa.model.sid.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        accountDao.save(account);

        Expenditure expenditure = new Expenditure();
        expenditure.setMoney(2222.22f);
        expenditureDao.save(expenditure);
        int i = 1 / 1;
        return "done";
    }
}