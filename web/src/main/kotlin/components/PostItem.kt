package components

import app.saboten.common.entities.Post
import kotlinx.html.js.onClickFunction
import react.Props
import react.RBuilder
import react.fc
import styled.styledDiv
import utils.functionalComponent

external interface PostItemProps : Props {
    var post: Post
    var onClick: () -> Unit
}

private val postItem = fc<PostItemProps> { props ->
    styledDiv {

        attrs.onClickFunction = { props.onClick() }

    }
}

fun RBuilder.postItem(post: Post, onClick: () -> Unit) {
    functionalComponent(postItem) {
        this.post = post
        this.onClick = onClick
    }
}