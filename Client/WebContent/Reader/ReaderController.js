angular.module('ReaderApp', [])
.controller('ReaderCtrl', ['$scope','$http', function ($scope,$http) {
	$scope.selectedHTML="Reader1.html";
	$scope.selectedSearchHTML="searchById.html";
	$scope.resultBookId="";
	$scope.ISBN="";
	$scopeTitle="";
	$scope.book=[];
	$scope.checkOutBooks=[];
	$scope.borrowedBooks=[];
	$scope.booksForReservation=[];
	$scope.computeFine=[];
	$scope.statusOfReservedBooks=[];
	$scope.booksByPublisher=[];
	$scope.branches=[];
	$scope.publishers=[];
	$scope.checkOutSearchClicked=false;
	$scope.returnSearchClicked=false;
	$scope.reserveSearchClicked=false;
	$scope.readerId=getUrlVars()['readerId']; 
	
	$http.get("http://localhost:8080/Library_Management/webServices/Book/getAllPublishers")
	.success(function(data){
		$scope.publishers=data.Publisher;
		alert($scope.readerId);
		
      });
	
	$scope.searchById=function(bookId){
		$http.get("http://localhost:8080/Library_Management/webServices/Book/searchBook/"+bookId)
		.success(function(data){
				//alert("Login is successful")
			$scope.resultBookId=data.bookId;
			$scope.title=data.title;
			$scope.ISBN=data.ISBN;
			
	      });
		
		
	};
	$scope.searchByTitle=function(receivedtitle){
		$scope.resultBookId="";
		$scope.title="";
		$scope.ISBN="";
		$scope,book=[];
		$http.get("http://localhost:8080/Library_Management/webServices/Book/searchBookByTitle/"+receivedtitle)
		.success(function(data){
			$scope.book=data.Book;
			
	      });
		
		
	};
	$scope.searchByPublisher=function(receivedPublisher){
		$http.get("http://localhost:8080/Library_Management/webServices/Book/searchBookByPublisher/"+receivedPublisher)
		.success(function(data){
				//alert("Login is successful")
			$scope.book=data.Book;
			
	      });
		
		
	};
	$scope.myFunction=function(){
		$http.get("http://localhost:8080/Library_Management/webServices/Book/getAllBranch")
		.success(function(data){
			$scope.branches=data.branch;
			
	      });
		
		
	};
	$scope.searchCheckOutOptions=function(selectedBranch){
		$scope.checkOutSearchClicked=true;
		$http.get("http://localhost:8080/Library_Management/webServices/Book/bookCheckOutSelection/"+selectedBranch)
		.success(function(data){
			$scope.checkOutBooks=data.Book;
			
	      });
		
		
	};
	$scope.checkOut=function(checkOutBookId, branchId){
		$http.get("http://localhost:8080/Library_Management/webServices/Book/bookCheckOut" +
				"/branchId/"+branchId+"/bookId/"+checkOutBookId+"/readerId/"+$scope.readerId)
		.success(function(data){
                alert("CheckOut Succesful")	;		
	      });
		
		
	};
	$scope.searchBorrowedBook=function(){
		$scope.returnSearchClicked=true;
		$http.get("http://localhost:8080/Library_Management/webServices/Book/" +
				"bookReturnSelection/readerId/"+$scope.readerId)
		.success(function(data){
			$scope.borrowedBooks=data.Book;
			
	      });
		
		
	};
	$scope.returnBook=function(returnBookId){
		$http.get("http://localhost:8080/Library_Management/webServices/Book/bookReturn" +
				"/readerId/"+$scope.readerId+"/bookId/"+returnBookId)
		.success(function(data){
                alert("Return Succesful")	;		
	      });
		
		
	};
	$scope.searchBookForReservation=function(){
		$scope.reserveSearchClicked=true;
		$http.get("http://localhost:8080/Library_Management/webServices/Book/" +
				"bookReserveSelection")
		.success(function(data){
			$scope.booksForReservation=data.Book;
			
	      });
		
		
	};
	$scope.reserveBook=function(reserveBookId){
		$http.get("http://localhost:8080/Library_Management/webServices/Book/bookReserve/bookId/"+reserveBookId+"/readerId/"+$scope.readerId)
		.success(function(data){
                alert("Reservation Succesful")	;		
	      });
		
		
	};
	$scope.computeFine=function(){
		$http.get("http://localhost:8080/Library_Management/webServices/Book/computeFine/readerId/"+$scope.readerId)
		.success(function(data){
			$scope.computeFine=data.Book;		
	      });
		
		
	};
	$scope.statusOfReservedBooks=function(){
		$http.get("http://localhost:8080/Library_Management/webServices/Book/ReserveBookStatus/readerId/"+$scope.readerId)
		.success(function(data){
			$scope.statusOfReservedBooks=data.Book;		
	      });
		
		
	};
	
	$scope.searchByPulisher=function(selectedPublisher){
		$scope.checkOutSearchClicked=true;
		$http.get("http://localhost:8080/Library_Management/webServices/Book/getAllBooksbyPublisher/" +
				"publisherName/"+selectedPublisher)
		.success(function(data){
			$scope.booksByPublisher=data.Book;
			
	      });
		
		
	};
	
	$scope.Quit=function(){
		 window.location ="http://localhost:8080/Client"
		
		
	};
	
	function getUrlVars()
	{
		alert("Inside getURl params");
	    var vars = [], hash;
	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	    for(var i = 0; i < hashes.length; i++)
	    {
	        hash = hashes[i].split('=');
	        vars.push(hash[0]);
	        vars[hash[0]] = hash[1];
	    }
	    return vars;
	}
	
	
	
	
	
   
}]);