<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GYM CARRY - Join</title>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.js" type="text/javascript"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.css"/>
<link href="https://fonts.googleapis.com/css?family=Jua" rel="stylesheet"> <!-- 구글폰트 -->
<link rel="stylesheet" href="${path }/css/userLogin.css" type="text/css">
</head>
<body>
<header>
	<a href="${path }/"><img src="${path }/resources/logo.png" width="300px" height="200px"/></a>
	<div class="container">
		<div class="row">			
			<div class="col-xs-2 col-sm-2 col-md-2"></div>
			<div class="col-xs-8 col-sm-8 col-md-8">
				<form action="${path }/user/loginChk.do" method="post">
					<div class="paper row">
						<div class="col-xs-6 col-sm-6 col-md-6">
							<div class="paper-top">
								<strong>로그인</strong><br><br>
								<span class="paper-fnt">다시 돌아와 주셔서 기뻐요!</span>
							</div>
							<div class="join-select">
								<div class="facebook-ico"><a href="#" ><img src="${path }/resources/user/facebook-logo.png" width="20px" height="20px"/>페이스북으로 로그인</a></div>
								<div class="kakao-ico"><a href="#"><img src="${path }/resources/user/kakao-logo.png" width="20px" height="20px"/>카카오톡으로 로그인</a></div>
								<div class="naver-ico"><a href="${url }"><img src="${path }/resources/user/naver-ico.png" width="30px" height="30px"/>네이버로 로그인</a></div>					
							</div>	
						</div>
						<div class="col-xs-6 col-sm-6 col-md-6">
							<div class="form-input"><input type="text" placeholder="아이디" name="userid"/></div>
							<div class="form-input"><input type="password" placeholder="비밀번호" name="userpwd"/></div>
							<label><input type="checkbox" name="remember_login" value="1"/>나를 기억해 주세요.</label>
							<input type="submit" value="로그인"/>
							<div>
								<span>아직 계정이 없으신가요?</span><a href="${path }/user/userJoin.do">회원가입하기</a><br>
								<span>아이디, 비밀번호가 생각나지 않으세요?</span><br>
								<a href="${path }/user/findId.do">아이디 찾기</a><a href="${path }/user/findPwd.do">비밀번호 찾기</a>
								
							</div>
						</div>
					</div>	
				</form>			
				<div style="text-align:center;margin-top:10px">
					<img src="${path }/resources/logoct.png" width="70px" height="70px"/>		
				</div>		
			</div>
			<div class="col-xs-2 col-sm-2 col-md-2"></div>
		</div>
	</div>
</header>
<footer>
	<div>
		
	</div>
</footer>
</body>
</html>