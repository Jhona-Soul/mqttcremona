package mqtttesting.demo.repository;

import mqtttesting.demo.model.SensorTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface STRepository extends JpaRepository<SensorTemperatura, Integer> {
}
