package blog.dongguabai.bytetcc.sample.consumer.consumer.controller;
import blog.dongguabai.bytetcc.sample.consumer.consumer.dao.TransferDao;
import blog.dongguabai.bytetcc.sample.consumer.consumer.service.ITransferService;
import blog.dongguabai.bytetcc.sample.consumer.feign.server.IAccountService;
import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableCancel;
import org.bytesoft.compensable.CompensableConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Compensable(interfaceClass = ITransferService.class, simplified = true)
@RestController
public class SimplifiedController implements ITransferService {
	
	@Autowired
	private TransferDao transferDao;
	@Autowired
	private IAccountService acctService;
	
	@ResponseBody
	@RequestMapping(value = "/simplified/transfer", method = RequestMethod.POST)
	@Transactional
	public void transfer(@RequestParam String sourceAcctId, @RequestParam String targetAcctId, @RequestParam double amount) {
		this.acctService.decreaseAmount(sourceAcctId, amount);  // tb_account_one表中的1001用户扣款
		this.increaseAmount(targetAcctId, amount);  // try

		// throw new IllegalStateException("rollback!");
	}

	private void increaseAmount(String acctId, double amount) {
		int value = this.transferDao.increaseAmount(acctId, amount);
		if (value != 1) {
			throw new IllegalStateException("ERROR!");
		}
		System.out.printf("exec increase: acct= %s, amount= %7.2f%n", acctId, amount);
	}

	@CompensableConfirm
	@Transactional
	public void confirmTransfer(String sourceAcctId, String targetAcctId, double amount) {
		int value = this.transferDao.confirmIncrease(targetAcctId, amount);
		if (value != 1) {
			throw new IllegalStateException("ERROR!");
		}
		System.out.printf("done increase: acct= %s, amount= %7.2f%n", targetAcctId, amount);
	}

	@CompensableCancel
	@Transactional
	public void cancelTransfer(String sourceAcctId, String targetAcctId, double amount) {
		int value = this.transferDao.cancelIncrease(targetAcctId, amount);
		if (value != 1) {
			throw new IllegalStateException("ERROR!");
		}
		System.out.printf("exec decrease: acct= %s, amount= %7.2f%n", targetAcctId, amount);
	}

}