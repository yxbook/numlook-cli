/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.7.21 : Database - db_cli
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_cli` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_cli`;

/*Table structure for table `sys_admin` */

DROP TABLE IF EXISTS `sys_admin`;

CREATE TABLE `sys_admin` (
  `pk_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员ID、主键',
  `uk_account` varchar(45) NOT NULL COMMENT '登录账户',
  `password` varchar(200) NOT NULL COMMENT '密码',
  `authenticator` varchar(200) NOT NULL COMMENT 'account+salt 散列加密后的值',
  `salt` varchar(45) NOT NULL COMMENT '盐值',
  `nickname` varchar(45) NOT NULL COMMENT '管理员名称',
  `phone` varchar(45) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `address` varchar(200) DEFAULT NULL COMMENT '地址  预留字段',
  `status` tinyint(4) DEFAULT NULL COMMENT '是否启用：0: 已启用   1：已禁用',
  `delete_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除：0: 未删除   1：已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `sort_time` datetime DEFAULT NULL COMMENT '排序时间',
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `uk_account` (`uk_account`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `sys_admin` */

insert  into `sys_admin`(`pk_id`,`uk_account`,`password`,`authenticator`,`salt`,`nickname`,`phone`,`email`,`avatar`,`address`,`status`,`delete_flag`,`create_time`,`update_time`,`sort_time`) values (11,'17712078858','504890e62f4cb70cf4d3225f5cc7b57aecfd38e1e40b15933c3de21969e7ab9839c3ae19f5a200cbd4817d5e10f90849b12595d71fbfc107bb3ad3374a615182','210fe09fdce276882b3c1228a0e4a7710cbfa8619a6cd0badded8936aee6162dc56b379b927960b26fcc87d2fc5288cb5e4b46f3c361e147f7415c9303b7e9e0','b73aef93ee9533f53d32c32e1d4c8f00','大憨豆先生。','17712078858','17712078858@163.com',NULL,'江苏徐州',0,0,'2018-08-05 13:55:39','2018-08-18 20:38:30','2018-08-05 13:55:39'),(12,'jialin','86a9f5725595467a7adf5f172864e6f70c59c0f1816c5facc2c3d412668ddfee1ae5bb74fdb020352d38d5477d711760d6a5a70f38b2ca9fda571ffbcf9eb327','2238aa7547b3e6ad0ecb498714ccdce3469928602996c2a189552307ff55c8c216b1f267d130b6be48da6865c513ceb52c652bb19be2a2172ef89bc921165990','f4170bdf119126548d83382c54a1b81a','贾林','17602555742','17602555742@163.com',NULL,NULL,0,0,'2018-08-18 17:22:40',NULL,'2018-08-18 17:22:40'),(13,'zqg','af6999f4a754ba0e67f5e188db24cace48084cc1dafc3b5c8d59383780bde59b5ba6fcfc7f9cdff9167d3a8d50acdd606f70df746a797d1c2f68d30f9501269d','fb06698f78b8caf53e55f86011fb95b27ae8ff87709758afa63a3e4ceb89bf3a42e367bb0e7635866e4943fe5f33ecb34250401f518f9ca2cfc50e799ac029d6','4bd59ccaeb43360cf8303c52f460a3d8','张庆国','13770728155','zhangqingguo@163.com',NULL,NULL,1,0,'2018-08-18 17:56:22',NULL,'2018-08-18 17:56:22'),(14,'lyh','6987727400fd44596905384ee985efc72ce39efd23807f9e1345d1cb5be24e123da01553b5870c248761d5fbe6fd5ec5ab89afbef1841a926f6984fed8b19839','30741518041fa3680ff9f9b23d4309251be046de3de682425a052f5e022734e6637e52d69f628f5e6cc65bd1daeefa8e714ae4993a2a4b66ad6b5a7f49e7c8c0','536182361a7e462bcb0a1634eefe1912','憨憨','17368646626','17368646626@163.com',NULL,NULL,0,0,'2018-08-18 18:57:27','2018-08-19 18:21:43','2018-08-18 17:57:27'),(15,'lzf','0a2dbe75b251c5d8453e24d9e4eaf229089d6eaa6684daa9e30c724a41d9d7982457bce15c10162e3e5b84ea7a2ae87026483262b40a14dca3535a73510ebe5b','4013d6d594369d8d942a1a5557424f245e78d4a54c616de60046ba9f60625886cd80969eefc1589b5c79e9fff7461bbc621169acd7e32ca18cf24dac6b66f081','36769702b4f2756bad8166457ecfbf47','李志富','18305177508','lizhifu@nanjinbandou.com',NULL,NULL,0,0,'2018-08-18 17:58:17',NULL,'2018-08-18 17:58:17'),(16,'lk','95bb55059170024f67c2e007d60958cd9e8f8adbe64e7060175c87aaa069ef3cd493ea8559e106489d6d5145320a8591decaad19457606cd93225aa415425fb7','f30aa6fcc4c5c980395a576f15e6652429243046428484f362934fd52c77ba3740b9d9f345ae3959498ab277fb801020fc9d26e5697ca05a8e8e00b142d0292c','9a7a12d56f0ee08317e9bc99b0ac420c','李奎','13770728166','likui@163.com',NULL,NULL,1,0,'2018-08-18 17:59:28',NULL,'2018-08-18 17:59:28'),(17,'admin','cf3f34afa712b2a58b453496aba41fff48efae9d2b0360b3d922ce63bef647d9139efc00640ce8d68e333b23fe9b37721085b93b92789822c093d89c25cb1a20','4c7287658887f2d1e3f2f057809517977777a84bd89c3a46a62f4c7c6f92ca4000992ff1f3b74c528d031314b2c56041e714bacd25178432094ef0fa62c1b487','b110ee02fa605708d9ea3ca314181897','admin','18260701152','admin@github.com',NULL,NULL,0,0,'2018-08-18 18:00:11',NULL,'2018-08-18 18:00:11'),(18,'csy','1841c72c935fe25b1b620dee7756b78a4511b3fd14aa896ccc5a4da09b17d028df441cc2ab569fd802772567e94cb6c8a2b818c42fcb21b73f3bbe4d060d5dca','a226e29468399ab96100b36a3772d2fcb9ac8ee72a246d17abe04d2922bca038d3c61064984f811a664e24d27695ce8047e4ed82d0e7e6b8a2b05a6faae73451','3dabf98660077dcba8d3b3a6fe54e189','宜身','13951015878','yishen@163.com',NULL,NULL,0,0,'2018-08-18 18:01:46',NULL,'2018-08-18 18:01:46'),(19,'ghj','f098358f68f6a9d16556fa295cec407436536e3fb376dbd38a2fdea200b8e6afb7153a286c65c4113f50001ca44de8a17fe8183a88fd9cc011677b1e877af71f','bfec7685542eb5ab91ccc00cd437fff924ea6001a4cbbf7f83682d6fa16cf19fa06d1d8ee9cc04961c7b3e608bf6f317228d5ac810eb88745ea68f7b6abc3c79','451f671fe51708d5def78d150425b3a2','豪杰','15077840299','15077840299@163.com',NULL,NULL,1,0,'2018-08-18 18:02:38',NULL,'2018-08-18 18:02:38'),(20,'zw','d731d507e25744c70fcf3d9e68f87984d050acb2f44d17db2d76eb42643268d714fe2677b5fbe2db41bd6e5faac5671ac11f60675b46dd2e08f4054bb15246a9','77e51eab1fbca956cf88cf7ba04064aa04fedb1cc363e2f1cb5c0c0bba65dd4307c9e5af6659cbaaf2f4036951e7ccb8f9a7b3524692ca7fe3d7dd7ed7ffe6f4','0ee2cc0d14b65e97d2de9608d8f35b7a','周伟','18851827750','18851827750@163.com',NULL,NULL,1,0,'2018-08-18 18:03:25','2018-08-19 16:51:28','2018-08-18 18:03:25'),(21,'dxs','a375494f2814f1321d4290a179634f901a8387b6c966436a930af2d43a7ce1276d5c7ec1f7a1015f14b7b3bce8b2ef5f91046967dde99b6b9074613e62b92e7a','6348b550c6fea59c9eb13202a126734d6d82757e6f252b866d6cadfd95e223edc81609891e23345c074234b9e0882444b11cd21bc774887661c9eb609f31b4d7','2ade2d74137a5040d224d428e77237a5','戴炳宇','17712078858','2554557545@qq.com',NULL,NULL,1,0,'2018-08-18 18:04:05',NULL,'2018-08-18 18:04:05'),(22,'ah','01cb604474f067ddeffcc96ba24c7aeec3bc3d9a602cfae2187b66d5ebc20b99c5e5b389263fc1b53bc5e703597cd2efb5588f19702cddc89e0d291d4abc7643','bc0cc246a111fd9433bb69d90d35491b068ea5e249e26b0245a5a22b9872f0ce505f05b17add98d89d0bc46a36810d837c50f957290bfeea4da012bb0dd6534e','8b998d38684237720cf80a50e9891fb6','艾合','18362933159','aihe@github.com',NULL,NULL,0,0,'2018-08-18 18:28:42',NULL,'2018-08-18 18:28:42'),(23,'lw','808af7b4099534ee3c2f24f6004d0d69d1bbaef7122a7d08e8d3115342889879269858622ef3252e7f49c5f5e185915c96fe2f06e288b194e613d76c999edf06','1adf494bf5d2f352b62646d35d7adc68c0aa73fe57f32a109b730aa486d5e7241266352c3e31968995099725cff6e4f2f93b3f43f7269c8e3235ae5aaadd820a','41146e9622b01edcff2ca58f5736d557','刘威','15152128766','dqi8766@dingtalk.com',NULL,NULL,1,0,'2018-08-18 18:30:18',NULL,'2018-08-18 18:30:18'),(24,'ceshi','45a37dd1fbf523967b9ccdf3bfcc8dec369dc78a2e85458c8b90805d8e3ed58bac92e1116ea13250c223548689c324ae660c5e2b236608b107fec3451e850748','a3a1e48baec2807bc667cf1085bff3f2967a60bc4d264647310f61447ebe9534ab988ff489705b93827dedfd9a41963056696b51645141bc63a6b8a3b17ddf79','3c3b4f9d19e97330964df90d2958a346','测试','15785645896','ceshi@163.com',NULL,NULL,1,0,'2018-08-18 18:31:06',NULL,'2018-08-18 18:31:06');

/*Table structure for table `sys_admin_role` */

DROP TABLE IF EXISTS `sys_admin_role`;

CREATE TABLE `sys_admin_role` (
  `pk_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关键主键ID',
  `amdin_id` int(11) NOT NULL COMMENT '管理员ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `delete_flag` tinyint(4) DEFAULT '0' COMMENT '\n\n是否删除：0 ：未删除   1：已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`pk_id`),
  KEY `fk_admin` (`amdin_id`),
  KEY `fk_role` (`role_id`),
  CONSTRAINT `fk_admin` FOREIGN KEY (`amdin_id`) REFERENCES `sys_admin` (`pk_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`pk_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `sys_admin_role` */

insert  into `sys_admin_role`(`pk_id`,`amdin_id`,`role_id`,`delete_flag`,`create_time`,`update_time`) values (6,12,2,0,'2018-08-18 17:22:40',NULL),(8,13,1,0,'2018-08-18 17:56:22',NULL),(10,15,1,0,'2018-08-18 17:58:17',NULL),(11,16,1,0,'2018-08-18 17:59:28',NULL),(12,17,2,0,'2018-08-18 18:00:11',NULL),(13,18,1,0,'2018-08-18 18:01:46',NULL),(14,19,2,0,'2018-08-18 18:02:38',NULL),(21,21,2,0,'2018-08-18 20:28:41',NULL),(22,22,1,0,'2018-08-18 20:28:43',NULL),(23,23,1,0,'2018-08-18 20:28:45',NULL),(24,24,2,0,'2018-08-18 20:28:46',NULL),(25,11,2,0,'2018-08-18 20:38:30',NULL),(26,20,2,0,'2018-08-19 16:51:28',NULL),(27,14,2,0,'2018-08-19 18:21:43',NULL);

/*Table structure for table `sys_generator` */

DROP TABLE IF EXISTS `sys_generator`;

CREATE TABLE `sys_generator` (
  `pk_id` int(11) NOT NULL,
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_generator` */

/*Table structure for table `sys_resource` */

DROP TABLE IF EXISTS `sys_resource`;

CREATE TABLE `sys_resource` (
  `pk_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源ID、主键',
  `parent_id` int(11) NOT NULL COMMENT '父级资源ID，一级资源 父级ID 为 0',
  `uk_name` varchar(45) NOT NULL COMMENT '资源名称、 唯一',
  `path` varchar(45) DEFAULT NULL COMMENT '路由路径',
  `perms` varchar(200) DEFAULT NULL COMMENT '授权 例如：sys:user:save, 中间；隔开',
  `type` tinyint(4) NOT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `delete_flag` tinyint(4) DEFAULT '0' COMMENT '\n\n是否删除：0 ：未删除   1：已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `sys_resource` */

insert  into `sys_resource`(`pk_id`,`parent_id`,`uk_name`,`path`,`perms`,`type`,`order_num`,`delete_flag`,`create_time`,`update_time`) values (1,0,'系统设置','system','sys:system',0,1,0,'2018-07-27 22:54:11','2018-07-27 22:54:14'),(2,1,'管理员管理','admin','sys:admin:list',1,2,0,'2018-07-27 22:56:45','2018-07-27 22:56:47'),(3,1,'角色管理','role','sys:role',1,3,0,'2018-07-27 22:57:14','2018-07-27 22:57:16'),(4,1,'菜单管理','menu','sys:menus',1,4,0,'2018-07-27 22:57:40','2018-07-27 22:57:42'),(5,1,'数据库监控','druid','sys:druid',1,5,0,'2018-08-18 22:29:22','2018-08-18 22:29:24'),(6,0,'用户管理',NULL,'sys:user',0,6,0,'2018-08-19 00:40:59','2018-08-19 00:41:01'),(7,6,'用户列表','user','sys:user:list',1,7,0,'2018-08-19 00:41:36','2018-08-19 00:41:38'),(11,0,'日志管理','log','sys:log',0,12,0,'2018-08-19 15:27:47','2018-08-19 15:29:01'),(12,11,'事物日志','transaction','sys:transaction',1,13,0,'2018-08-19 15:30:55','2018-08-19 15:30:55');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `pk_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID、主键',
  `uk_name` varchar(45) NOT NULL COMMENT '角色名称  唯一',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注',
  `delete_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除：0：未删除  1：已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`pk_id`),
  UNIQUE KEY `uk_name` (`uk_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`pk_id`,`uk_name`,`remark`,`delete_flag`,`create_time`,`update_time`) values (1,'管理员','普通管理员',0,'2018-07-17 13:13:44','2018-07-17 13:13:46'),(2,'超级管理员','超级管理员',0,'2018-07-17 13:14:04','2018-08-19 09:58:32'),(3,'市级管理员','地方行政区 管理员',0,'2018-08-19 01:26:14','2018-08-19 01:26:50'),(4,'省级管理员','特别行政区、省辖 管理员',1,'2018-08-19 01:19:47','2018-08-19 01:19:47'),(5,'测试','测试数据',0,'2018-08-19 16:51:56','2018-08-19 16:51:56');

/*Table structure for table `sys_role_resource` */

DROP TABLE IF EXISTS `sys_role_resource`;

CREATE TABLE `sys_role_resource` (
  `pk_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `resource_id` int(11) NOT NULL COMMENT '资源ID',
  `delete_flag` tinyint(4) DEFAULT '0' COMMENT '\n\n是否删除：0 ：未删除   1：已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`pk_id`),
  KEY `fk_rr_role` (`role_id`),
  KEY `fk_rr_resource` (`resource_id`),
  CONSTRAINT `fk_rr_resource` FOREIGN KEY (`resource_id`) REFERENCES `sys_resource` (`pk_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_rr_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`pk_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_resource` */

insert  into `sys_role_resource`(`pk_id`,`role_id`,`resource_id`,`delete_flag`,`create_time`,`update_time`) values (1,1,1,0,'2018-07-27 22:57:55','2018-07-27 22:57:56'),(2,1,2,0,'2018-07-27 22:58:09','2018-07-27 22:58:11'),(10,4,2,0,'2018-08-19 01:19:47','2018-08-19 01:19:47'),(11,4,5,0,'2018-08-19 01:19:47','2018-08-19 01:19:47'),(12,4,7,0,'2018-08-19 01:19:47','2018-08-19 01:19:47'),(19,3,4,0,'2018-08-19 01:26:50','2018-08-19 01:26:50'),(20,3,5,0,'2018-08-19 01:26:50','2018-08-19 01:26:50'),(21,3,6,0,'2018-08-19 01:26:50','2018-08-19 01:26:50'),(22,3,7,0,'2018-08-19 01:26:50','2018-08-19 01:26:50'),(29,2,2,0,'2018-08-19 09:58:32','2018-08-19 09:58:32'),(30,2,3,0,'2018-08-19 09:58:32','2018-08-19 09:58:32'),(31,2,4,0,'2018-08-19 09:58:32','2018-08-19 09:58:32'),(32,2,5,0,'2018-08-19 09:58:32','2018-08-19 09:58:32'),(33,2,6,0,'2018-08-19 09:58:32','2018-08-19 09:58:32'),(34,2,7,0,'2018-08-19 09:58:32','2018-08-19 09:58:32'),(36,2,1,0,NULL,NULL),(37,5,6,0,'2018-08-19 16:51:56','2018-08-19 16:51:56'),(38,5,7,0,'2018-08-19 16:51:56','2018-08-19 16:51:56'),(39,5,11,0,'2018-08-19 16:51:56','2018-08-19 16:51:56'),(40,5,12,0,'2018-08-19 16:51:56','2018-08-19 16:51:56');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
