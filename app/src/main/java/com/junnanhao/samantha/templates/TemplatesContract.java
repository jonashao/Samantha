/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.junnanhao.samantha.templates;

import android.support.annotation.NonNull;

import com.junnanhao.samantha.base.BasePresenter;
import com.junnanhao.samantha.base.BaseView;
import com.junnanhao.samantha.model.entity.Template;

import java.util.List;


/**
 * This specifies the contract between the view and the presenter.
 */
 interface TemplatesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showTemplates(List<Template> templates);

        void showAddTemplate();

        void showTemplateDetailsUi(String templateId);

        void showLoadingTemplatesError();

        void showNoTemplates();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadtemplates(boolean forceUpdate);

        void addNewTemplate();

        void openTemplateDetails(@NonNull Template requestedTemplate);

        void completeTemplate(@NonNull Template completedTemplate);

        void activateTemplate(@NonNull Template activeTemplate);

        void clearCompletedtemplates();

        void setFiltering(int requestType);

        int getFiltering();
    }
}
