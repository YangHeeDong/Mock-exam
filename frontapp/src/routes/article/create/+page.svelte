<script lang="ts">
    
    import { browser } from '$app/environment';

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
            window.location.href = "/article/detail/"+rs.data.article.id;
        })
        .catch(error => {
            console.error(error);
        });
    }
</script>

<h3>Create Article</h3>
<form on:submit|preventDefault={submitLoginForm}>
    <div class="input-group">
        <span class="input-group-text bg-dark text-white border-dark">Title</span>
        <input type="text" name="title" class="form-control border-dark" placeholder="Enter title" aria-describedby="basic-addon1">
    </div>

    <div class="input-group mt-1">
        <span class="input-group-text bg-dark text-white border-dark">body</span>
        <textarea name="body" class="form-control border-dark" aria-label="With textarea" rows="20" placeholder="Enter body"></textarea>
    </div>

    <div class="mt-2">
        <a class="btn-sm btn-primary text-decoration-none " href="/article/list">목록으로</a>
        <button type="submit" class="btn-sm bg-dark text-white  text-decoration-none float-end ">등록</button>
    </div>
</form>
