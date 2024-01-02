<script lang="ts">
    function submitLoginForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        form.title.value = form.title.value.trim();
        if (form.title.value.length === 0) {
            alert('title is required');
            form.title.focus();
            return;
        }
        form.body.value = form.body.value.trim();
        if (form.body.value.length === 0) {
            alert('body is required');
            form.body.focus();
            return;
        }
        fetch('http://localhost:8080/api/v1/articles', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: form.title.value,
                body: form.body.value
            })
        })
            .then(response => response.json())
            .then(rs => {
                location.href("/article/detail/"+rs.data.article.id)
            })
            .catch(error => {
                console.error(error);
            });
    }
</script>

<h1>글작성</h1>
<form class="mt-3" on:submit|preventDefault={submitLoginForm}>
    <div>
        <input type="text" name="title" placeholder="제목을 입력하세요" />
    </div>
    <div class="mt-2">
        <textarea name="body" placeholder="내용을 입력하세요" ></textarea>
    </div>
    <button type="submit">Login</button>
</form>
