const header = $("meta[name='_csrf_header']").attr('content');
const token = $("meta[name='_csrf']").attr('content');

const main = {
    init: function () {
        const _this = this;

        $('#btn-post-save').on('click', function () {
            _this.postSave();
        });

        $('#btn-post-update').on('click', function () {
            _this.postUpdate();
        })

        $('#btn-post-delete').on().click(function () {
            _this.postDelete();
        });

        $('#btn-user-modify').on('click', function () {
            _this.userModify();
        });

        $('#btn-comment-submit').on('click', function () {
            _this.commentSave();
        });

        document.querySelectorAll('#comment-update').forEach(function (item) {

            item.addEventListener('click', function () {
                console.log("init")
                console.log(item)
                const form = this.closest('form');
                _this.commentUpdate(form);
            });
        });
    },

    postSave : function () {
        const data = {
            title : $('#title').val(),
            content : $('#content').val(),
            writer : $('#writer').val()
        }

        if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            if(confirm("등록하시겠습니까?")) {
                $.ajax({
                    type: 'POST',
                    url: '/api/post',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    alert("게시물이 등록되었습니다.")
                    window.location.href = "/post/list";
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }
    },

    postUpdate : function () {
        const data = {
            id : $('#id').val(),
            title : $('#title').val(),
            content : $('#content').val(),
        }

        console.log(data);

        if(!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
            alert('공백 또는 입력하지 않은 부분이 있습니다.');
            return false;
        } else {
            if(confirm("수정하시겠습니까?")) {
                $.ajax({
                    type: 'PUT',
                    url: `/api/post/${data.id}`,
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    alert("게시물이 수정되었습니다.")
                    window.location.href = "/post/detail/"+data.id;
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }

    },

    postDelete : function () {
        const data = {
            id : $('#id').val()
        }
        if(confirm("삭제하시겠습니까?")) {
            $.ajax({
                type: 'DELETE',
                url: `/api/post/${data.id}`,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert("게시물이 삭제되었습니다.")
                window.location.href = "/post/list";
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }

    },

    commentUpdate : function (form) {
        const data = {
            id : form.querySelector('#id').value,
            postId : form.querySelector('#postId').value,
            comment: form.querySelector('#comment-content').value
        }
        if(!data.comment || data.comment.trim() === "") {
            alert('공백 또는 입력하지 않은 부분이 있습니다.');
            return false;
        }
        const con_chk = confirm("수정하시겠습니까?");
        if (con_chk === true) {
            $.ajax({
                type: 'PUT',
                url: `/api/post/${data.postId}/comment/${data.id}`,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    commentDelete : function (postId, commentId) {
      if (confirm("삭제하시겠습니까?")) {
          $.ajax({
              type: 'DELETE',
              url: `/api/post/${postId}/comment/${commentId}`,
              beforeSend: function (xhr) {
                  xhr.setRequestHeader(header, token);
              },
              dataType: 'json'
          }).done(function () {
              alert("댓글이 삭제되었습니다.")
              location.reload();
          }).fail(function (error) {
              alert(JSON.stringify(error));
          });
      }
    },

    commentSave: function () {

        console.log("init")

        const data = {
            postId: $('#postId').val(),
            comment: $('#comment').val()
        }



        if (!data.comment || data.comment.trim() === "") {
            alert('공백 또는 입력하지 않은 부분이 있습니다.');
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: `/api/post/${data.postId}/comment`,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert("댓글이 등록되었습니다.")
                location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    userModify: function () {
        const data = {
            id: $('#id').val(),
            modifiedDate: $('#modifiedDate').val(),
            username: $('#username').val(),
            password: $('#password').val(),
            nickname: $('#nickname').val()
        }
        if (!data.nickname || data.nickname.trim() === "" || !data.password || data.password.trim() === "") {
            console.log(data.nickname);
            console.log(data.password);
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else if (!/(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\W)(?=\S+$).{8,16}/.test(data.password)) {
            alert("비밀번호는 8~16자 영문 대소문자, 숫자, 특수문자를 사용하세요.");
            $('#password').focus();
            return false;
        } else if (!/^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,10}$/.test(data.nickname)) {
            alert("닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
            $('#nickname').focus();
            return false;
        }
        const con_check = confirm("수정하시겠습니까?");
        if (con_check === true) {
            $.ajax({
                type: "PUT",
                url: "/api/user",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                contentType: "application/json; charset=UTF-8",
                data: JSON.stringify(data)
            }).done(function () {
                alert("회원수정이 완료되었습니다.");
                window.location.href = "/post/list";
            }).fail(function (error) {
                if (error.status === 5000) {
                    alert("이미 사용중인 닉네임입니다.");
                    $('#nickname').focus();
                } else {
                    alert(JSON.stringify(error));
                }
            })
        }
    }
};

main.init();