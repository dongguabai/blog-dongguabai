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

	/**
	 * post 请求：localhost:7080/simplified/transfer?sourceAcctId=1001&targetAcctId=2001&amount=1。
	 * 也就是从 `tb_account_one` 表中的 1001 号用户给 `tb_account_two` 表中的 2001 用户转 1 块钱；
	 * 查看数据库发现数据变化正常，即表示项目基本使用成功
	 */
	@ResponseBody
	@RequestMapping(value = "/simplified/transfer", method = RequestMethod.POST)
	@Transactional
	public void transfer(@RequestParam String sourceAcctId, @RequestParam String targetAcctId, @RequestParam double amount) {
		this.acctService.decreaseAmount(sourceAcctId, amount);
		this.increaseAmount(targetAcctId, amount);
	}

	private void increaseAmount(String acctId, double amount) {
		this.transferDao.increaseAmount(acctId, amount);
		System.out.printf("exec increase: acct= %s, amount= %7.2f%n", acctId, amount);
	}

	@CompensableCancel
	@Transactional
	public void cancelTransfer(String sourceAcctId, String targetAcctId, double amount) {
		this.transferDao.cancelIncrease(targetAcctId, amount);
		System.out.printf("undo decrease: acct= %s, amount= %7.2f%n", targetAcctId, amount);
	}

	/**
	 * 如果无特殊逻辑需要处理, confirm也可以省略
	 */
	@CompensableConfirm
	@Transactional
	public void confirmTransfer(String sourceAcctId, String targetAcctId, double amount) {
		System.out.printf("done decrease: acct= %s, amount= %7.2f%n", targetAcctId, amount);
	}

}
