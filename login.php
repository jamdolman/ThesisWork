<?php
// PHP 7.0 �̻󿡼� mysql_connect�� mysql_* �Լ��� �ȸ��� (���� 7.24)
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
            or die("������ ���࿡ �����߽��ϴ�. ������ �����ʴ� �����̰ų�, ������ ��ü�� ������ ������ �ֽ��ϴ�."); 
        
    // echo "User Found";
                
    $rows = mysqli_num_rows($query_exec);

    if($rows == 0) {
        echo 'check ID or PW';
    }
    // ���⼭ ū����ǥ�� ��������ǥ�� ����� �޶����� ������.
    else  {
        echo "Success";
    }
?>

