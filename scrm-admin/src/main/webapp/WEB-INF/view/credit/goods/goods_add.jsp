<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/assets/js/common/bootstrapValidator.js"></script>
</head>
<form class="form-horizontal tasi-form" method="post"
      action="${pageContext.request.contextPath}/credit/goods/saveData?callbackMethod=refreshTable"
      data-toggle="validator"
      onsubmit="return saveData();">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×</button>
        <div class="col-sm-12">
            <h4 class="pull-left page-title">积分商城</h4>
            <ol class="breadcrumb pull-right">
                <li><a data-target="divload" data-div="baseContainer">积分商城</a></li>
                <li class="active">添加商品</li>
            </ol>
        </div>
    </div>
    <div class="modal-body">
        <fieldset>
            <legend><h5>基本信息</h5></legend>
            <div class="form-group">
                <table>
                    <tr>
                        <td>商品名称:</td>
                        <td><input type="text" id="goodsName" name="goodsName" class="form-control" placeholder="请输入商品名称" required></td>
                        <td> 商品形式:</td>
                        <td>
                            <div id="goodsType" class="col-sm-4 col-md-offset-2">
                                <div class="radio radio-info radio-inline">
                                    <input type="radio" id="goodsType1" value="1" name="goodsType">

                                    <label for="goodsType1">实物</label>
                                </div>
                                <div class="radio radio-info radio-inline">
                                    <input type="radio" id="goodsType2"  value="2" name="goodsType">
                                    <label for="goodsType2">虚拟</label>
                                </div>
                            </div>
                            <%--<input type="radio" name="goodsType" class="form-control" value="1" checked>实物
                            <input type="radio" name="goodsType" class="form-control" value="2" disabled>虚拟--%>
                        </td>
                    </tr>
                    <tr>
                        <td>商品副标题:</td>
                        <td><input type="text" class="form-control" id="goodsSubtitle" name="goodsSubtitle" placeholder="请输入商品副标题" required></td>
                        <td> 封面图片:</td>
                        <td>
                            <a href="javascript:void(0)" class="onePicUpload btn">封面图上传</a>
                            <input type="hidden" id="goodsCoverUrl"/>
                            <a href='' target='_blank' id='viewPic'>
                                <img src='' width='80' height='50'/>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td>商品库存:</td>
                        <td><input type="text" class="form-control" id="goodsStock" name="goodsStock" placeholder="请输入商品库存" onKeyUp="value=value.replace(/[^0-9]/g,'')"></td>
                        <td>商品运费:</td>
                        <td> <input type="text" class="form-control" id="freight" name="freight" onkeyup="limitTwoPoint(this);" onafterpaste="limitTwoPoint(this);" placeholder="请输入商品运费"></td>
                    </tr>
                </table>
            </div>
        </fieldset>
        <hr>
        <fieldset>
            <legend><h5>兑换规则</h5></legend>
            <div class="form-group">
                <div class="form-group">
                    <label>积分使用规则
                        <select id="integralRuleId" onchange="changeIntegralRule()">
                            <option value="1">积分+现金</option>
                            <option value="2">纯现金</option>
                            <option value="3">纯积分</option>
                        </select>
                        <input type="text" class="form-control" id="goodsExchangeIntegral" onKeyUp="value=value.replace(/[^0-9]/g,'')">积分
                        <input type="text" class="form-control" id="goodsPrice" onkeyup="limitTwoPoint(this);" onafterpaste="limitTwoPoint(this);">元
                    </label>
                    <label>同设备限次：
                        <input type="radio" name="sameDeviceLimited" value="1" checked>是
                        <input type="radio" name="sameDeviceLimited" value="2">否
                        <input type="text" id="sameDeviceLimitedNumber" class="form-control" onKeyUp="value=value.replace(/[^0-9]/g,'')">次
                    </label>
                    <label>同用户限次：
                        <input type="radio" name="sameUserLimited" value="1" checked>是
                        <input type="radio" name="sameUserLimited" value="2">否
                        <input type="text" id="sameUserLimitedNumber" class="form-control" onKeyUp="value=value.replace(/[^0-9]/g,'')">次
                    </label>
                </div>
            </div>
        </fieldset>
    </div>
    <div class="modal-footer">
        <button type="submit" class="btn btn-info waves-effect waves-light">保存</button>
        <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">取消</button>
    </div>
</form>
<div id="cover_img_modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content"></div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        $(".select2").select2({

        });
    });
</script>
<script src="${pageContext.request.contextPath}/assets/js/module/credit/goods/goods_add.js"></script>
</html>
