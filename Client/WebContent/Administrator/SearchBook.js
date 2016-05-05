/**
 * 
 */
/**
 * 
 */


var app = angular.module('searchBook', []);

app.controller('searchCtrl', function($scope, $http){
	$scope.isId = true;
	$scope.isTitle = false;
	$scope.isPublisher = false;
	//$scope.reader = false;
	
	$scope.change = function(){
	
			$scope.Id="";
			$scope.title="";
			$scope.publisher="";
		if($scope.isId){
			Sscope.isId=true;
			$scope.isTitle = false;
			$scope.isPublisher = false;
		}
		else if($scope.isTitle){
			Sscope.isTitle=true;
			$scope.isId = false;
			$scope.isPublisher = false;
		}
		else($scope.isPublisher){
			$scope.isPublisher=true;
			$scope.isTitle = false;
			$scope.isId = false;
		}
	};
	
	$scope.login=function(){
		if($scope.admin){
		$http.get("http://localhost:8080/Library_Management/webServices/AdminLoginService/"+$scope.userName)
		.success(function(data){
			if($scope.userName==data.userName && $scope.password==data.password){
				//alert("Login is successful")
				 window.location = "Administrator.html";
			}
			else{
				alert("Unauthenticated user");
			}
	
	      });
		}
		else{
			$http.get("http://localhost:8080/Library_Management/webServices/Reader/searchReaderByID/"+$scope.readerId)
			.success(function(data){
				if($scope.readerId==data.readerId){
					alert("Login is successful")
				}
				else{
					alert("Unauthenticated user");
				}
		
		      });
			
		}
	};
});