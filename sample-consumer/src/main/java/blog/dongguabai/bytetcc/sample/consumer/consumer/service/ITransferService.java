package blog.dongguabai.bytetcc.sample.consumer.consumer.service;

public interface ITransferService {

	public void transfer(String sourceAcctId, String targetAcctId, double amount);

}
