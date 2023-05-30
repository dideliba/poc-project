package com.poc.user.domain.request;

import lombok.Data;
import lombok.experimental.Delegate;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfoList<E> implements List<E> {
    @Valid
    @Delegate
    private List<E> list = new ArrayList<>();
}