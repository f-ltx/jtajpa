package cn.ltx.jtajpa.dao.sid;

import cn.ltx.jtajpa.model.sid.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account, Integer> {
}
