<script>
    let articles = $state([]);

    $effect(() => {
        fetch('http://localhost:8080/api/v1/articles')
            .then(response => response.json())
            .then(json => articles = json.data.articles);
    });
</script>

<div class="container items-center">
    <h1 class="text-3xl font-bold underline">Articles</h1>
    <hr>
    <table class="table-fixed mt-5">
        <thead>
          <tr>
            <th>Id</th>
            <th>Title</th>
            <th>createDate</th>
          </tr>
        </thead>
        <tbody>
            {#each articles as article}
            <tr>
                <td>
                    <span>{article.id}</span>
                </td>
                <td class="color">
                    <a href="/article/detail/{article.id}">{article.title}</a>
                </td>
                <td>
                    <span>{article.createDate}</span>
                </td>
            </tr>
        {/each}
        </tbody>
      </table>
      <a href="/article/create">등록하기</a>

</div>