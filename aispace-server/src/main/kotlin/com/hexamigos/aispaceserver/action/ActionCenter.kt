package com.hexamigos.aispaceserver.action

import com.hexamigos.aispaceserver.action.email.EmailAction
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
class ActionCenter(val actionManager: ActionManager) {

}