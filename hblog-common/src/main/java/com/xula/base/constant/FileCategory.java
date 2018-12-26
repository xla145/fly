package com.xula.base.constant;

/**
 * 
 * 上传文件分类
 * 
 *
 * @author caixb
 */
public enum FileCategory {
	
	product(1,"商品文件相关","yue-product/file/"),sys(2,"系统文件相关","yue-sys/file/"),user(3,"用户文件相关","yue-user/file/"),test(4,"测试文件相关","test/file/");

	private Integer type;
	private String name;
	private String dir;

	FileCategory(Integer type, String name, String dir) {
		this.type = type;
		this.name = name;
		this.dir = dir;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public static String getUploadDir(Integer type){
		FileCategory[] arr = FileCategory.values();
		for (FileCategory ucc : arr) {
			if (ucc.getType() == type) {
				return ucc.getDir();
			}
		}
		return "";
	}
}
