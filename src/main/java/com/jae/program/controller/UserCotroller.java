package com.jae.program.controller;


import com.jae.framework.entity.Pagination;
import com.jae.framework.entity.ResultVo;
import com.jae.program.model.User;
import com.jae.program.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户控制
 * @author Jae
 *
 */
@Controller
@RequestMapping("/admin")
public class UserCotroller {
	
	@Autowired
	UserService userService;
	
	/**
	 * 用户列表
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listUser.html")
	public ResultVo listUser(
			@RequestParam(defaultValue="1",required=false) int page,
			@RequestParam(defaultValue="10",required=false) int rows){
		ResultVo result = new ResultVo();
		Pagination<User> pg = userService.createPageQueryModelByHql("from User").setPage(page).setRows(rows).query();
		result.setResults("data",pg.getRows());
		result.setResults("total", pg.getTotal());
		result.setResults("totalPage", pg.getTotalPage());
		result.setResults("page", page);
		result.setResults("rows", rows);
		return result;
	}
	
	/**
	 * 用户详情
	 * @param
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/detailUser.html")
	public ResultVo listUser(int id){
		ResultVo result = new ResultVo();
		User user = userService.getById(id);
		result.setResults(user);
		return result;
	}
	
	/**
	 * 用户增加
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addUser.html")
	public ResultVo insertUser(User user){
		ResultVo result = new ResultVo();
		User userResult = userService.save(user);
		result.setResults(userResult);
		return result;
	}
	
	/**
	 * 用户修改
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateUser.html")
	public ResultVo updateUser(User user){
		ResultVo result = new ResultVo();
		User userResult = userService.update(user);
		result.setResults(userResult);
		return result;
	}
	
	/**
	 * 用户删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteUser.html")
	public ResultVo deleteUser(String ids){
		ResultVo result = new ResultVo();
		for(String id : ids.split(",")){
			User user = userService.getById(id);
			userService.delete(user);
		}
		result.setErrMsg("删除成功!");
		return result;
	}
	
}
