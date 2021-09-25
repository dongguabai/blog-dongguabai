package blog.dongguabai.bytetcc.sample.consumer.consumer.service.impl;

import blog.dongguabai.bytetcc.sample.consumer.consumer.dao.TransferDao;
import blog.dongguabai.bytetcc.sample.consumer.consumer.service.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("transferServiceCancel")
public class TransferServiceCancel implements ITransferService {

	@Autowired
	private TransferDao transferDao;

	@Transactional
	public void transfer(String sourceAcctId, String targetAcctId, double amount) {
		this.transferDao.cancelIncrease(targetAcctId, amount);
		System.out.printf("exec decrease: acct= %s, amount= %7.2f%n", targetAcctId, amount);
	}

}
