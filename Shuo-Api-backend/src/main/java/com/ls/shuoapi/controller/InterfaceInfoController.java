package com.ls.shuoapi.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ls.shuoapi.annotation.AuthCheck;
import com.ls.shuoapi.common.BaseResponse;
import com.ls.shuoapi.common.DeleteRequest;
import com.ls.shuoapi.common.ErrorCode;
import com.ls.shuoapi.common.ResultUtils;
import com.ls.shuoapi.constant.UserConstant;
import com.ls.shuoapi.exception.BusinessException;
import com.ls.shuoapi.exception.ThrowUtils;
import com.ls.shuoapi.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.ls.shuoapi.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.ls.shuoapi.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.ls.shuoapi.model.entity.InterfaceInfo;
import com.ls.shuoapi.model.entity.User;

import com.ls.shuoapi.service.InterfaceInfoService;
import com.ls.shuoapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 接口信息 接口
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
       //逻辑校验
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // todo 参数校验
//        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);

        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);


        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
 
        boolean result = interfaceInfoService.save(interfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        // 参数逻辑校验
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // todo 参数校验

        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     * @param interfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest) {
        //参数逻辑校验
        if (interfaceInfoUpdateRequest == null || interfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // todo 参数校验
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, interfaceInfo);

        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<InterfaceInfo> getInterfaceInfoVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
//        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVO(interfaceInfo, request));
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 分页获取列表（仅管理员）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();

        // 查询条件
        Long id = interfaceInfoQueryRequest.getId();
        String name = interfaceInfoQueryRequest.getName();
        String description = interfaceInfoQueryRequest.getDescription();
        String url = interfaceInfoQueryRequest.getUrl();
        String requestHeader = interfaceInfoQueryRequest.getRequestHeader();
        String responseHeader = interfaceInfoQueryRequest.getResponseHeader();
        Integer status = interfaceInfoQueryRequest.getStatus();
        String method = interfaceInfoQueryRequest.getMethod();
        Long userId = interfaceInfoQueryRequest.getUserId();

        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();

        // 构造查询条件
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        
        // 精确查询条件
        queryWrapper.lambda()
            .eq(interfaceInfoQueryRequest.getId() != null, InterfaceInfo::getId, interfaceInfoQueryRequest.getId())
            .eq(interfaceInfoQueryRequest.getStatus() != null, InterfaceInfo::getStatus, interfaceInfoQueryRequest.getStatus())
            .eq(StringUtils.isNotBlank(interfaceInfoQueryRequest.getMethod()), InterfaceInfo::getMethod, interfaceInfoQueryRequest.getMethod())
            .eq(interfaceInfoQueryRequest.getUserId() != null, InterfaceInfo::getUserId, interfaceInfoQueryRequest.getUserId());
            
        // 模糊查询条件
        queryWrapper.lambda()
            .like(StringUtils.isNotBlank(interfaceInfoQueryRequest.getName()), InterfaceInfo::getName, interfaceInfoQueryRequest.getName())
            .like(StringUtils.isNotBlank(interfaceInfoQueryRequest.getDescription()), InterfaceInfo::getDescription, interfaceInfoQueryRequest.getDescription())
            .like(StringUtils.isNotBlank(interfaceInfoQueryRequest.getUrl()), InterfaceInfo::getUrl, interfaceInfoQueryRequest.getUrl());
        
        // 排序条件
        if (StringUtils.isNotBlank(interfaceInfoQueryRequest.getSortField())) {
            boolean isAsc = "asc".equalsIgnoreCase(interfaceInfoQueryRequest.getSortOrder());
            queryWrapper.orderBy(true, isAsc, interfaceInfoQueryRequest.getSortField());
        }
        
        // 执行分页查询
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }


//    /**
//     * 分页获取列表（封装类）
//     *
//     * @param interfaceInfoQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/list/page/vo")
//    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoVOByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
//            HttpServletRequest request) {
//        long current = interfaceInfoQueryRequest.getCurrent();
//        long size = interfaceInfoQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
//                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
//        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVOPage(interfaceInfoPage, request));
//    }

//    /**
//     * 分页获取当前用户创建的资源列表
//     *
//     * @param interfaceInfoQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/my/list/page/vo")
//    public BaseResponse<Page<InterfaceInfo>> listMyInterfaceInfoVOByPage(@RequestBody InterfaceInfoQueryRequest interfaceInfoQueryRequest,
//            HttpServletRequest request) {
//        if (interfaceInfoQueryRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User loginUser = userService.getLoginUser(request);
//        interfaceInfoQueryRequest.setUserId(loginUser.getId());
//        long current = interfaceInfoQueryRequest.getCurrent();
//        long size = interfaceInfoQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
//                interfaceInfoService.getQueryWrapper(interfaceInfoQueryRequest));
//        return ResultUtils.success(interfaceInfoService.getInterfaceInfoVOPage(interfaceInfoPage, request));
//    }

    // endregion




}
