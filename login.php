<?php
// PHP 7.0 이상에선 mysql_connect등 mysql_* 함수들 안먹힘 (지금 7.24)
  $connect = mysqli_connect("localhost","root","" , "thesiswork");
//  $dbname = "thesiswork";
//  $dbconn = mysql_select_db($dbname,$connect);
  
 header('Content-type: application/json');
  
mysqli_set_charset($connect , "utf8");

 $ID = $_POST['ID'];
 $PW = $_POST['PW'];
 
    $query_search = "select * from customer where ID = '".$ID."' AND PW = '".$PW. "'";
    
   // $PW = $_POST['PW'];
    $query_exec = mysqli_query($connect , $query_search)
            or die("쿼리문 실행에 실패했습니다. 버전에 맞지않는 쿼리이거나, 쿼리문 자체에 문제가 있을수 있습니다."); 
        
    // echo "User Found";
                
    $rows = mysqli_num_rows($query_exec);

    if($rows == 0) {
        echo 'check ID or PW';
    }
    // 여기서 큰따음표던 작은따음표던 결과의 달라짐은 없었다.
    else  {
        echo "Success";
    }
?>

