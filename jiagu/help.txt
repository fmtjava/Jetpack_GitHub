	-login <username> 			首次使用必须先登录 <360用户名>
		<password>				<登录密码>
	-importsign <keystore_path> 		导入签名信息 <密钥路径>
		<keystore_password> 			<密钥密码>
		<alias> 				<别名>
		<alias_password>			<别名密码>
	-importmulpkg <mulpkg_filepath>		导入多渠道配置信息，txt格式
	-showsign				查看已配置的签名信息
	-showmulpkg				查看已配置的多渠道信息
	-deletemulpkg			清除已配置的多渠道信息
	-help					显示帮助信息
	-config 				配置加固可选项
	----------------------可选增强服务-------------------------------
		[-crashlog]				【崩溃日志分析】
		[-x86]					【x86支持】
		[-analyse]				【加固数据分析】
		[-nocert]				【跳过签名校验】
		[-piracy]				【盗版监测】
	----------------------高级加固选项-------------------------------
		[-vmp]					【全VMP保护】
		[-data]					【本地数据文件保护】
		[-assets]				【资源文件保护】
		[-filecheck]				【文件完整性校验】
		[-ptrace]				【Ptrace防注入】
		[-so]					【SO文件保护】
		[-dex2c]				【dex2C保护】
		[-string_obfus]				【字符串加密】
		[-dex_shadow]				【DexShadow】
		[-so_private]				【SO防盗用】
		[-double_check]				【双开检测】
	-----------------------------------------------------------------
	-config_so			配置需要加固的SO文件，以空格分隔
	-config_assets			配置需要忽略的资源文件，以空格分隔
	-config_so_private		配置防盗用的SO文件，以空格分隔

	-showconfig				显示已配置加固项
	-version				显示当前版本号
	-update					升级到最新版本
	-jiagu <inputAPKpath> 			加固命令 <APK路径>
		<outputPath> 				<输出路径>
		[-autosign] 				【自动签名】
		[-automulpkg] 				【自动多渠道】
		[-pkgparam mulpkg_filepath]		【自定义文件生成多渠道】