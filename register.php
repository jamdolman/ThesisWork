<?php

$con = mysqli_connect("localhost" , "root" , "" , "thesiswork");

mysqli_set_charset($con , "utf8");

if (mysqli_connect_errno($con))
{
    echo "Failed to connect MySQL :" . mysqli_connect_error();
}

$ID = $_POST['ID'];
$PW = $_POST['PW'];
$NAME = $_POST['NAME'];

$result = mysqli_query($con , "insert into customer (ID , PW , NAME) values ('$ID' , '$PW' , '$NAME')");

if ($result) {
    echo 'success';
}
else {
    echo 'failure';
}

mysqli_close($con);

?>
