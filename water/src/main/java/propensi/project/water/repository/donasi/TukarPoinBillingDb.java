package propensi.project.water.repository.donasi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.project.water.model.PoinReward.TukarPoinBillingModel;


@Repository
public interface TukarPoinBillingDb extends JpaRepository<TukarPoinBillingModel, Long> {}
