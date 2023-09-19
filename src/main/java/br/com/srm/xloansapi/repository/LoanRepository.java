package br.com.srm.xloansapi.repository;

import br.com.srm.xloansapi.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query(value = "SELECT e.* FROM emprestimo e " +
            "WHERE e.id = :loanId",
            nativeQuery = true)
    Loan findByLoanId(@Param("loanId") Long loanId);
}
