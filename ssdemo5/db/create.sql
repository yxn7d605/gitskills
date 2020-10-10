CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `oa_code` varchar(32) NOT NULL COMMENT 'OA编号',
  `name` varchar(100) NOT NULL COMMENT '用户姓名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `post_code` varchar(32) NOT NULL COMMENT '职务编号',
  `post_name` varchar(100) DEFAULT NULL COMMENT '职务名称',
  `dept_code` varchar(32) NOT NULL COMMENT '部门编号',
  `dept_name` varchar(100) DEFAULT NULL COMMENT '部门编号',
  `org_code` varchar(32) NOT NULL COMMENT '机构编号',
  `org_name` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `org_tcode` varchar(32) DEFAULT NULL COMMENT '机构类别编码，1001-总行各厅局，1002-分行',
  `status` int NOT NULL DEFAULT '1' COMMENT '用户状态：1-有效，2-无效',
  `created_by` varchar(32) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='用户表';

CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(32)  NOT NULL COMMENT '角色编号',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `dept_code` varchar(32) NOT NULL COMMENT '部门编号',
  `dept_name` varchar(100) DEFAULT NULL COMMENT '部门编号',
  `org_code` varchar(32) NOT NULL COMMENT '机构编号',
  `org_name` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `org_tcode` varchar(32) DEFAULT NULL COMMENT '机构类别编码，1001-总行各厅局，1002-分行',
  `status` int NOT NULL DEFAULT '1' COMMENT '角色状态：1-有效，2-无效',
  `created_by` varchar(32) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='角色表';

CREATE TABLE `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(32)  NOT NULL COMMENT '权限编号',
  `name` varchar(100) NOT NULL COMMENT '权限名称',
  `parent_code` varchar(32) NOT NULL COMMENT '父权限编号',
  `status` int NOT NULL DEFAULT '1' COMMENT '权限状态：1-有效，2-无效',
  `created_by` varchar(32) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='权限表';

CREATE TABLE `resource` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(32)  NOT NULL COMMENT '资源编号',
  `name` varchar(100) NOT NULL COMMENT '资源名称',
  `uri` varchar(200) NOT NULL COMMENT '资源uri',
  `permission_code` varchar(32) NOT NULL COMMENT '资源所属权限',
  `created_by` varchar(32) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='资源表';

CREATE TABLE `user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `oa_code` varchar(32) NOT NULL COMMENT 'OA编号',
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `created_by` varchar(32) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='用户角色关联表';

CREATE TABLE `role_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `parent_code` varchar(32) DEFAULT NULL COMMENT '权限编号',
  `created_by` varchar(32) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='角色权限关联表';