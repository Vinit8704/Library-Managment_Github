/**
 * 
 */


var app = angular.module('loginApp', []);

app.controller('loginCtrl', function($scope, $http){
	$scope.admin = false;
	//$scope.reader = false;
	
	$scope.change = function(){
	
			$scope.userName="";
			$scope.password="";
			$scope.readerId="";
		$scope.admin = !$scope.admin;
	};
	$scope.close = function(){
	window.close();	
	};
	$scope.login=function(){
		if($scope.admin){
		$http.get("http://localhost:8080/Library_Management/webServices/AdminLoginService/"+$scope.userName)
		.success(function(data){
			if($scope.userName==data.userName && $scope.password==data.password){
				//alert("Login is successful")
				// window.location = "Reader.html?readerId="+data.userName;
				 window.location ="Administrator.html"
			}
			else{
				alert("Unauthenticated user");
			}
	
	      });
		}
		else{
			$http.get("http://localhost:8080/Library_Management/webServices/Reader/searchReaderByID/"+$scope.readerId)
			.success(function(data){
				if($scope.readerId===data.readerId){
					 window.location = "Reader.html?readerId="+data.readerId;
				}
				else{
					alert("Unauthenticated user");
				}
		
		      });
			
		}
	};
	
});