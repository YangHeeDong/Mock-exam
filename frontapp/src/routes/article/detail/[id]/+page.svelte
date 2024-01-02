<script>
    import {page} from '$app/stores';
    import { onMount } from "svelte";
    
    // type Aricle = {
    //             id: String;
    //             title: string;
    //             body: number;
    //             createDate : String;
    //             modifyDate : String;
    //         }
    
    // let article:Article = [];
    let article = [];
    // 마운트 후에
    onMount(() => {
        
        // 페이지 매개변수로부터 id 추출
        const id = $page.params['id'];

        fetch('http://localhost:8080/api/v1/articles/'+id)
                .then(response => response.json())
                .then(json => article = json.data.article);
    });

        
    
</script>

<div class="container">
    <div>제목 : {article.title}</div>
    <div>내용 : {article.body}</div>
    <div>작성일 : {article.createDate}</div>

    <a href="/article/list">
        목록으로
    </a>
</div>