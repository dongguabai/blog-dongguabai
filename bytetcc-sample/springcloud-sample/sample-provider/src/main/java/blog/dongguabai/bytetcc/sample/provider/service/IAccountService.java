package blog.dongguabai.bytetcc.sample.provider.service;

public interface IAccountService {

	public void increaseAmount(String accountId, double amount);

	public void decreaseAmount(String accountId, double amount);

}
