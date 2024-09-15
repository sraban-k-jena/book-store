function onRemove(id){
    if(confirm("Do you want to Remove ?")){
        // location.href="/remove-book?id"+id;
        location.href=`/remove-book?id=${id}`;
    }
}