<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="navbar navbar-default" id="navbar">
  <script type="text/javascript">
    try{ace.settings.check('navbar' , 'fixed')}catch(e){}
  </script>
  <div class="navbar-container" id="navbar-container">
    <div class="navbar-header pull-left">
      <a href="javascript:;" class="navbar-brand">
        智高考专家后台
      </a><!-- /.brand -->
    </div><!-- /.navbar-header -->

    <div class="navbar-header pull-right" role="navigation">
      <ul class="nav ace-nav">
        <li class="light-blue">
          <a data-toggle="dropdown" href="#" class="dropdown-toggle">
            <i class="icon-user"></i>
            <i class="icon-caret-down"></i>
          </a>
          <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
            <li class="divider"></li>
            <li>
              <a href="/expert/admin/login/logout.do" id="logout-btn">
                <i class="icon-off"></i>
                退出
              </a>
            </li>
          </ul>
        </li>
      </ul><!-- /.ace-nav -->
    </div><!-- /.navbar-header -->
  </div><!-- /.container -->
</div>
