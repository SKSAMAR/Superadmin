package com.fintech.superadmin.activities;


/**
@AndroidEntryPoint
public class MainActivity extends BaseActivity {

    ActivityMainBinding activityMainBinding;
    AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        setContentView(activityMainBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();
        if (savedInstanceState == null) {
            setFragment(new SignInFragment());
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(activityMainBinding.ScreenFrame.getId(), fragment);
        fragmentTransaction.commit();
    }

}
**/