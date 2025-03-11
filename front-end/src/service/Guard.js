import React, {Component} from "react";
import {Navigate, useLocation} from "react-router-dom";
import ApiService from "./apiService.js";

export const protectedRoute = ({element: Component}) => {
    const location = useLocation();
    return ApiService.isAuthenticated() ? (
        Component
        ) : (
            <Navigate to="/login" replace state={{from: location}}/>
        );
};

