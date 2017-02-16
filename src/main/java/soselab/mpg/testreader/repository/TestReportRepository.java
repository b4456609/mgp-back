package soselab.mpg.testreader.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import soselab.mpg.testreader.model.TestReport;

public interface TestReportRepository extends MongoRepository<TestReport, String> {
    TestReport findOneByCreatedDate(long timestamp);
}
